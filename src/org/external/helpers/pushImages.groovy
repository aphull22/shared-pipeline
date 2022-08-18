#!/usr/bin/groovy
package org.external.helpers

def run(ecrRepositoryName, tag, account) {
    stage('Pushing image') {
        echo "Region: us-east-1"
        echo "Pushing Image to ${account}"
        env.account = "${account}"
        env.ecrRepositoryName = "${ecrRepositoryName}"
        env.tag = "${tag}"
        sh label: '', script: '''#!/usr/bin/env bash
                                 eval $(aws ecr get-login --region=us-east-1 --registry-ids ${account} --no-include-email)
                                 docker push \${account}.dkr.ecr.us-east-1.amazonaws.com/\${ecrRepositoryName}:\${tag}
                                 docker rmi \${account}.dkr.ecr.us-east-1.amazonaws.com/${ecrRepositoryName}:\${tag}'''
    }
}
