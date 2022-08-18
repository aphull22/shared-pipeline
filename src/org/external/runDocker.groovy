#!/usr/bin/groovy
package org.external

def run(command) {
	 sh("sudo docker ${command}")
}
