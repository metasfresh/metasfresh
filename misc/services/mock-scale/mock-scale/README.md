
For the moment, two requests are supported:

    GetInstantGrossWeighRequest
    GetStableGrossWeighRequest

The response for each of those requests can be overridden via environment variable.
The environment variables can be overridden when running the Docker image, if not, the values specified in `application.properties` are used.

The variable `response.soehenle.get-instantGrossWeight` represents the response for `GetInstantGrossWeighRequest`
and the variable `response.soehenle.get-stableGrossWeight` represents the response for `GetStableGrossWeighRequest`.

As an example, one can run the Docker image with the following comand:

`docker run -e response.soehenle.get-instantGrossWeight="001001N 11 kg" -e response.soehenle.get-stableGrossWeight="A1234567001001N 12,095 kg" -p 8090:8090  dockerImage`, 
where `dockerImage` represents the name of the image specified when it was built.

Following the command: 
- the application is running on port 8090
- the variable `response.soehenle.get-instantGrossWeight` is overridden to `001001N 11 kg`
- the variable `response.soehenle.get-stableGrossWeight` is overridden to `A1234567001001N 12,095 kg` 