UPDATE m_forecast
SET datepromised = (
    SELECT datepromised
    FROM (SELECT 1                    seq,
                 startdate::Timestamp datepromised
          FROM c_period p
          WHERE p.c_period_id = m_forecast.c_period_id

          UNION

          SELECT 2                                seq,
                 to_timestamp(fiscalyear, 'YYYY') datepromised /* eg. 2020.01.01 */
          FROM c_year y
          WHERE y.c_year_id = m_forecast.c_year_id

          UNION

          SELECT 3 seq, to_timestamp('2010-01-01', 'YYYY-MM-DD') datepromised
         ) options
    ORDER BY seq
    LIMIT 1
),
    Updated=TO_TIMESTAMP('2020-08-19 08:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE datepromised IS NULL
;
