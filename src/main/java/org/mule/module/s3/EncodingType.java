/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3;

import com.amazonaws.services.s3.model.ListObjectsRequest;

/**
 * This enum is needed to display the proper options for encoding types in Studio.
 * Methods of the Amazon SDK for Java only receive a String. For an example,
 * see {@link ListObjectsRequest#withEncodingType(String)}
 *
 */
public enum EncodingType {
	NOT_ENCODED("Not encoded", null),
	URL("URL", "url")
	;
	
	private EncodingType(final String text, final String sdkValue) {
		this.text = text;
		this.sdkValue = sdkValue;
	}
	
	private final String text;
	private final String sdkValue;
	
	@Override
	public String toString() {
		return text;
	}
	
	public String sdkValue() {
		return this.sdkValue;
	}
}
