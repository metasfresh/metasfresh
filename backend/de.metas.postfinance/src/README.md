
In the `wsit-client.xml` file specify the path to the `B2BService.xml` file which contains the right credentials
for the desired system.

Example: `file:////opt/test/test/B2BService.xml`

In the `B2BService.xml` file, the credentials have to be specified in the following two tags:
  - ` <sc1:CallbackHandler name="usernameHandler" default="userid" />`
  - ` <sc1:CallbackHandler name="passwordHandler" default="password"/>`

Change `userid` and `password` according to your needs