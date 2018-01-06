select db_alter_table('M_InOut', 'alter table M_InOut drop column if exists CreatePackage');
select db_alter_table('M_InOutConfirm', 'alter table M_InOutConfirm drop column if exists CreatePackage');
select db_alter_table('DD_Order', 'alter table DD_Order drop column if exists CreatePackage');

select db_alter_table('M_InOut', 'alter table M_InOut drop column if exists NoPackages');
select db_alter_table('M_InOutConfirm', 'alter table M_InOutConfirm drop column if exists NoPackages');
select db_alter_table('DD_Order', 'alter table DD_Order drop column if exists NoPackages');

select db_alter_table('M_InOut', 'alter table M_InOut drop column if exists TrackingNo');
select db_alter_table('M_InOutConfirm', 'alter table M_InOutConfirm drop column if exists TrackingNo');
select db_alter_table('DD_Order', 'alter table DD_Order drop column if exists TrackingNo');

select db_alter_table('M_InOut', 'alter table M_InOut drop column if exists ShipDate');
select db_alter_table('M_InOutConfirm', 'alter table M_InOutConfirm drop column if exists ShipDate');
select db_alter_table('DD_Order', 'alter table DD_Order drop column if exists ShipDate');




