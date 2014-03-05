S3 UPLOAD DEMO
=============

INTRODUCTION
  This is minimalistic a demo about uploading contents to an S3 bucket. This demo covers the creation an versioning enabling of buckets,  
  the object uploading, and versioning listing.  

HOW TO DEMO:
  1. Set the following system properties:
  	a. s3.accessKey. This is the access key of your Amazon Account
    b. s3.secetKey. This is the secret key of your Amazon Account (Do not share it!)
    c. s3.bucketName. This is a test bucket where the objects will be created. Remember that you need to choose a non existing bucket name
  2. Run the "SetupFlow" only once, in order to create a bucket and enable its versioning. 
    a.  You can verify its creation simply by hitting with an http client like Mozilla Firefox or curl http://XXX.s3.amazonaws.com where XXX is your new bucket name. 
        No credentials are required since the bucket was created with public read permissions. You can also check that the bucket is empty        
  3. Run the "UploadFlow". This will upload an image from the Mule homepage into the selected bucket. Each time you run it, a new version of the object will be created.
  	a. You can verify the content upload by simply hitting http://XXX.s3.amazonaws.com/mulelogo.jpg. Again, no credentials are needed. 

HOW IT WORKS:
   - The UploadFlow downloads the file to upload to s3 using an http endpoint 
   - The S3 Connector uploads the returned input stream. It recognizes that such input stream has http metadata, so not passing the content length 
   has no performance penalties
   - A list with the versions at the bucket is returned

WHAT HAS NOT BEEN DEMO:
    Deletion operations over buckets and objects, object copying and bucket and bucket listing.  
    
