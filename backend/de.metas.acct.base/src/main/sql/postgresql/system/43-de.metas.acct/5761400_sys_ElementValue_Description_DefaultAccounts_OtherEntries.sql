


Update c_elementvalue ev set description = x.elementValueDescription,
                             updated = '2025-07-22 19:14:36.450',
                             updatedby = 99
FROM migration_data.default_account_mapping x
WHERE ev.value = x.accountvalue;