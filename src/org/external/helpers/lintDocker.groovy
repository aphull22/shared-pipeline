#!/usr/bin/groovy
package org.external.helpers


def run(ecrRepositoryName) {
    this.runDocker('run --rm -i hadolint/hadolint < services/${ecrRepositoryName}/Dockerfile', true)
}
