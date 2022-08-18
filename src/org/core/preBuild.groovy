#!/usr/bin/groovy
package org.core

def run() {
	checkoutSource = new org.core.helpers.checkoutSource()
	setProperty = new org.core.helpers.setProperty()

	setProperty.job()
	setProperty.environment()
	checkoutSource.run()
}
