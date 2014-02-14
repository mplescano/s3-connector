
package org.mule.module.s3.oauth;

import javax.annotation.Generated;

@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-07-18T05:10:21-05:00", comments = "Build UNKNOWN_BUILDNUMBER")
public interface OAuth2Adapter extends OAuthAdapter
{

    /**
     * Build authorization URL and create the inbound endpoint for the callback
     *
     * @param extraParameters Extra query string parameters that should be added to the authorization URL
     * @return The authorization URL
     */
    String authorize(java.util.Map<String, String> extraParameters, String accessTokenUrl, String redirectUri) throws UnableToAcquireRequestTokenException;

    /**
     * Acquire access token and secret
     *
     * @throws UnableToAcquireAccessTokenException
     *
     */
    void fetchAccessToken(String accessTokenUrl, String redirectUri) throws UnableToAcquireAccessTokenException;

    void setExpiration(java.util.Date value);

    boolean hasTokenExpired();

    void refreshAccessToken(String accessTokenUrl) throws UnableToAcquireAccessTokenException;

    String getRefreshToken();
}
