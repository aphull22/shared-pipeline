#!/usr/bin/env groovy
import java.text.SimpleDateFormat
import groovy.json.*

//EXPOSE ADDITIONAL FUNCTIONS TO JENKINSFILES
class devClasses {
	def builder = new org.external.builder() 

}


def call(body){
	preBuild = new org.sdp.core.preBuild()
	postBuild = new org.sdp.core.postBuild()
	
	//DECLARE NODE
	node {
		//DECLARE WRAPPER CLSSES
    		wrap([$class: 'BuildUser']) {
    			wrap([$class: 'MaskPasswordsBuildWrapper']) {
           			wrap([$class: 'TimestamperBuildWrapper'] ) {
               				wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'xterm']) {
                  				//CLEAN UP WORKSPACE
						step([$class: 'WsCleanup'])
						try {
							//RUN PREBUILD
							//DECLARE ENVIRONMENT VARIABLES
							//DECLARE JOB PROPERTIES
							preBuild()
							
							//EXECUTE BODY OF JENKINSFILE
							body.delegate = new devClasses()
							body()
						} catch (err) {
							currentBuild.result = "FAILURE"
						}
						//RUN POSTBUILD
						postBuild()
					}
				}
			}
		}
	}
}
