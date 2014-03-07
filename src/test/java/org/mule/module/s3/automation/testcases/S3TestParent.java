package org.mule.module.s3.automation.testcases;

import java.util.UUID;

import org.mule.modules.tests.ConnectorTestCase;

public class S3TestParent extends ConnectorTestCase {
	
	protected String randomString() {
		return UUID.randomUUID().toString();
	}
	
}
