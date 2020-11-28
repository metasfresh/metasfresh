drop index if exists C_ConversionType_UQ;
create unique index C_ConversionType_UQ on C_ConversionType(Value);
