update ad_sysconfig
set value = 60*30*1000 /*30 minutes*/
WHERE name IN
      (
       'reports.remoteServletInvoker.connectTimeout',
       'reports.remoteServletInvoker.readTimeout'
          )
;
