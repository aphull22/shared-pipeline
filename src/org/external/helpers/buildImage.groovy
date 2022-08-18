#!/usr/bin/groovy
package org.external.helpers

def run(String ecrRepositoryName) {
	def describeECRImagesCmd = "aws ecr describe-images --region ${awsRegion} --registry-id ${ecrAWSAccountIdProd} --repository-name ${ecrRepositoryName} --output json --query 'sort_by(imageDetails,& imagePushedAt)[-1].imageTags[0]'"
        def findLastSemanticVerCmd = "jq . --raw-output |  sed 's/\"//g'"
        def incVersionCmd = 'perl -pe \'s/^((\\d+\\.)*)(\\d+)(.*)$/$1.($3+1).$4/e\''
        def fullCmd = "${describeECRImagesCmd} | ${findLastSemanticVerCmd} | ${incVersionCmd}"
        imageVersion = sh(returnStdout: true, script: fullCmd).trim()
        if (!imageVersion) {
            imageVersion = '1.0.0'
        }
        if (imageVersion == "[]..1") {
            imageVersion = '1.0.0'
        }
        echo 'Next Image Version: ' + imageVersion
        def gitHash=sh (returnStdout: true, script: "git rev-parse HEAD").trim()
        def dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss")
        def date = new Date()
        def buildDate = (dateFormat.format(date)) 
 sh("sudo docker build --label org.label-schema.build-date=${buildDate} --label org.label-schema.vendor=Audiomack --label org.label-schema.name=${ecrRepositoryName} --label org.label-schema.version=${imageVersion} --label org.label-schema.vcs-ref=${gitHash} -t ${ecrRepositoryName}:${imageVersion} -t ${ecrRepositoryName}-scanning-repo:latest --no-cache --pull -f services/${ecrRepositoryName}/Dockerfile .")
}
