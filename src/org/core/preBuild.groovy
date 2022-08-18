#!/usr/bin/groovy
package org.core

def run(Map setup) {
	checkoutSource = new org.core.helpers.checkoutSource()
	setProperty = new org.core.helpers.setProperty()

	setProperty.job(setup)
	setProperty.environment(setup)
	checkoutSource.run(setup)
}
