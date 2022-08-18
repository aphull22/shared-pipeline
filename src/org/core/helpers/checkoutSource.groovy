#!/usr/bin/groovy
package org.core.helpers

def run(Map setup) {
    def scmVars = checkout([$class: 'GitSCM', branches: scm.branches, userRemoteConfigs: [[credentialsId: 'audiomack-machine-user', url: gitUrl]]])
    def fullCommitHash = scmVars.GIT_COMMIT
    def commitHash = fullCommitHash.take(12)
    echo "Using git commit: ${fullCommitHash}"
    return commitHash
}
