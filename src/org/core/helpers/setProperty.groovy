#!/usr/bin/groovy
package org.core.helpers

def environment(Map setup) {
	// GIT
	env.gitUrlRoot = "https://github.com/audiomack/audiomack-services"
	env.gitUrl = "${gitUrlRoot}.git"
	env.gitCommitsUrl = "${gitUrlRoot}/commits"

	// ECR
	env.ecrRepositoryName = setup.ecrRepositoryName
	env.awsRegion = 'us-east-1'
	env.ecrAWSAccountId = '567316365753'
	env.ecrRegistryUrl = "${ecrAWSAccountIdProd}.dkr.ecr.${awsRegion}.amazonaws.com"
	env.ecrRepositoryFQN = "${ecrRegistryUrl}/${ecrRepositoryName}"

}

def job(Map setup){
	properties(
        	[
        		disableConcurrentBuilds(),
        		buildDiscarder(
            		logRotator(artifactDaysToKeepStr: '1',
            			artifactNumToKeepStr: '10',
            			daysToKeepStr: '1',
            			numToKeepStr: '10'
            			)
                	),
                	[$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false],
                	parameters([
                        	booleanParam(defaultValue: false, description: 'Build reports-content image', name: 'reportscontent'),
                        	booleanParam(defaultValue: false, description: 'Build reports-content image', name: 'contentimport'),
    			]),
                	pipelineTriggers([])
        	]
	)
}

