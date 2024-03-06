
In the SysConfig with name `de.metas.postfinance.PostFinanceURLStreamHandler.B2BServiceURL` add the path to the `B2BService.xml` file that contains the right credentials for PostFinance.
This file has to be mounted on the Server.

Local testing example: `file:/C:/metasfresh/testfiles/B2BService.xml`

In the `B2BService.xml` file, the credentials have to be specified in the following two tags:
  - ` <sc1:CallbackHandler name="usernameHandler" default="userid" />`
  - ` <sc1:CallbackHandler name="passwordHandler" default="password"/>`

Change `userid` and `password` according to your needs