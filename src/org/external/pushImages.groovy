#!/usr/bin/groovy
package org.external

def run(ecrRepositoryName, tag, account) {
    stage('Pushing image') {
        echo "Region: us-east-1"
        echo "Pushing Image to ${account}"
        env.account = "${account}"
        env.ecrRepositoryName = "${ecrRepositoryName}"
        env.tag = "${tag}"
        sh label: '', script: '''#!/usr/bin/env bash
                                 cred=$(aws sts assume-role --role-arn arn:aws:iam::${account}:role/JenkinsCrossAccountRole --role-session-name "devops-amicleaner")
                                 export AWS_ACCESS_KEY_ID=$(echo ${cred} | jq .Credentials.AccessKeyId | xargs)
                                 export AWS_SECRET_ACCESS_KEY=$(echo ${cred} | jq .Credentials.SecretAccessKey | xargs)
                                 export AWS_SESSION_TOKEN=$(echo ${cred} | jq .Credentials.SessionToken | xargs)
                                 export AWS_DEFAULT_REGION=\'us-east-1\'
                                 eval $(aws ecr get-login --region=us-east-1 --registry-ids ${account} --no-include-email)
                                 docker tag \${ecrRepositoryName}:\${tag} \${account}.dkr.ecr.us-east-1.amazonaws.com/${ecrRepositoryName}:\${tag}
                                 docker push \${account}.dkr.ecr.us-east-1.amazonaws.com/\${ecrRepositoryName}:\${tag}
                                 docker rmi \${account}.dkr.ecr.us-east-1.amazonaws.com/${ecrRepositoryName}:\${tag}'''
    }
}
