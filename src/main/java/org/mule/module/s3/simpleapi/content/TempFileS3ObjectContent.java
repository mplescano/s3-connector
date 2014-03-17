/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.module.s3.simpleapi.content;

import java.io.File;

public class TempFileS3ObjectContent extends FileS3ObjectContent {

	public TempFileS3ObjectContent(File file) {
		super(file);
	}

	public boolean delete() {
		return this.file.delete();
	}

}
