#!/usr/bin/groovy
package org.external

def run(ecrRepositoryName) {
    this.runDocker('run --rm -i hadolint/hadolint < services/${ecrRepositoryName}/Dockerfile', true)
}
