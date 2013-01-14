S3 UPLOAD STUDIO DEMO
=====================

INTRODUCTION

This is a minimalistic demo of how a simple S3 upload operation looks like in Mule Studio.

HOW TO DEMO

1. Import your project into Studio
2. Set up your credentials and bucket
   a. Open the file src/main/resources/s3Demo.properties
   b. Fill in the properties and save the file
3. Run the example
   a. Run the Mule Project
   b. Check the contents of your bucket 
       - Open the bucket in the S3 Management Console, located in the following url
           https://console.aws.amazon.com/s3/home?#
       - Verify that the file is not there.
   c. With a browser (or curl) open http://localhost:8081/getFile
       - This will trigger a flow where Mule will download a file from the web and save it to your bucket.
       - As a response, you will get the file name, plus the latest modification time, which should be very close to the present time and date.
   c. If you want, tou can check the contents of your bucket once again
       - Open the bucket in the S3 Management Console, located in the following url
           https://console.aws.amazon.com/s3/home?#
       - Verify that the file is there, with the proper modification date/time.


