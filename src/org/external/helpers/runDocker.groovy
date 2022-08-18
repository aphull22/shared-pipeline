#!/usr/bin/groovy
package org.external.helpers

def run(command) {
	 sh("sudo docker ${command}")
}
