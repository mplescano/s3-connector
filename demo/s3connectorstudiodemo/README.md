S3 STUDIO DEMO
=====================

INTRODUCTION

This is a minimalistic demo of how a simple S3 upload operation looks like in Anypoint Studio.

HOW TO DEMO

1. Import your project into Studio
2. Set up your credentials and bucket
   a. Open the file src/main/app/mule-app.properties
   b. Fill in the properties and save the file
3. Run the example
   a. Run the Mule Project
   b. With a browser (or curl) open http://localhost:8081/demo
       - This will trigger a flow where Mule will download a file from the web and save it to your bucket.
   c. After running the demo check the console with each of the operation outputs.


