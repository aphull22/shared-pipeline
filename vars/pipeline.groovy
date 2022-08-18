#!/usr/bin/env groovy
import java.text.SimpleDateFormat
import groovy.json.*

class devClasses {
	def buildImage = new org.external.buildImage() 
	def lintDocker = new org.external.lintDocker()
	def pushImages = new org.external.pushImages()
	def removeAutoDeleteImages = new org.external.removeAutoDeleteImages()
	def runDocker = new org.external.runDocker.groovy


}
def call(Map setup, body){

	node {
    		wrap([$class: 'BuildUser']) {
    			wrap([$class: 'MaskPasswordsBuildWrapper']) {
           			wrap([$class: 'TimestamperBuildWrapper'] ) {
               				wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'xterm']) {
                  				step([$class: 'WsCleanup'])
						try {
							preBuild(setup)
							body.delegate = new devClasses()
							body()
						} catch (err) {
							currentBuild.result = "FAILURE"
						}
						postBuild(setup)
					}
				}
			}
		}
	}
}
