
metas-ts 2015-02-04:

In this "pachted" lib we added our own version of org/zkoss/zkmax/zul/render/Combobox2Default
See task "08398 Dropdown Box List to wide or big".

Note that we then removed the java file from our day-to-day builds 
(it was /de.metas.adempiere.adempiere.zkwebui.base/src/main/java/org/zkoss/zkmax/zul/render/Combobox2Default.java)
to avoid the duplicate class problem, even if both versions are currently identical.

Further note that the patched version can be retrieved using
<dependency>
	<groupId>org.zkoss.zk</groupId>
	<artifactId>zkmax</artifactId>
	<version>3.6.3-metas-patched</version>
</dependency>