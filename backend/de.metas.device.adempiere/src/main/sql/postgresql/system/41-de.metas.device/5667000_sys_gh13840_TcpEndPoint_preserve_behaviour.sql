
-- preserve the old behavior
UPDATE AD_SysConfig SET Value='de.metas.device.scales.endpoint.TcpConnectionReadLineEndPoint' WHERE name like 'de.metas.device.%.Endpoint.Class';
