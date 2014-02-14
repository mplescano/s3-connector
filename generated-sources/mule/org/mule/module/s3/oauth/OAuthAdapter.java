
package org.mule.module.s3.oauth;

import javax.annotation.Generated;

@Generated(value = "Mule DevKit Version 3.4.0", date = "2013-07-18T05:10:21-05:00", comments = "Build UNKNOWN_BUILDNUMBER")
public interface OAuthAdapter {

    /**
     * Retrieve OAuth verifier
     *
     * @return A String representing the OAuth verifier
     */
    String getOauthVerifier();

    /**
     * Set OAuth verifier
     *
     * @param value OAuth verifier to set
     */
    void setOauthVerifier(String value);

    /**
     * Retrieve access token
     */
    String getAccessToken();

    /**
     * Set access token
     *
     * @param value
     */
    void setAccessToken(String value);

    /**
     * Set the callback to be called when the access token and secret need to be saved for
     * later restoration
     *
     * @param saveCallback Callback to be called
     */
    void setOauthSaveAccessToken(SaveAccessTokenCallback saveCallback);

    /**
     * Set the callback to be called when the access token and secret need to be restored
     *
     * @param restoreCallback Callback to be called
     */
    void setOauthRestoreAccessToken(RestoreAccessTokenCallback restoreCallback);

    /**
     * Get the callback to be called when the access token and secret need to be saved for
     * later restoration
     */
    SaveAccessTokenCallback getOauthSaveAccessToken();

    /**
     * Get the callback to be called when the access token and secret need to be restored
     */
    RestoreAccessTokenCallback getOauthRestoreAccessToken();

    void hasBeenAuthorized() throws NotAuthorizedException;
}
