#!/usr/bin/groovy
package org.external

def run(List<String> projectList){
	def buildImage = new org.external.helpers.buildImage() 
	def lintDocker = new org.external.helpers.lintDocker()
	def pushImages = new org.external.helpers.pushImages()
	def removeAutoDeleteImages = new org.external.helpers.removeAutoDeleteImages()
	def runDocker = new org.external.helpers.runDocker.groovy
	
	//ecrAWSAccountId is declared in src.org.helpers.setProperty()
	ecrAWSAccountId = env.ecrAWSAccountId
	
	projectList = projectList
	for(item in projectList) {
		stage('Build image and push to ECR for $item') {
			def ecrRepositoryName = item
        		imageVersion = buildImage(ecrRepositoryName)

			stage('Security Scan for $item') {
                        	aquaMicroscanner imageName: "${ecrRepositoryName}:${imageVersion}", notCompliesCmd: "exit 0", onDisallowed: "ignore", outputFormat: "html"
                        }

                       createRepo(ecrRepositoryName)
                       pushImages(ecrRepositoryName, imageVersion, ecrAWSAccountId)
                       runDocker("rmi -f ${ecrRepositoryName}:${imageVersion}")
                }
         }           
}
