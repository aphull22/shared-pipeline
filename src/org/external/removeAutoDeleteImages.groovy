#!/usr/bin/groovy
package org.external

def run() {
	runDocker = new org.external.runDocker()
	runDocker.run('image prune -a -f --filter "label=autodelete=true"')
	echo 'removed autodelete images'
}
