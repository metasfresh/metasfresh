UPDATE m_forecast
SET datepromised = (
    SELECT startdate::Timestamp
    FROM c_period p
    WHERE p.c_period_id = m_forecast.c_period_id

    UNION

    SELECT to_timestamp(fiscalyear, 'YYYY') /* eg. 2020.01.01 */
    FROM c_year y
    WHERE y.c_year_id = m_forecast.m_forecast_id

    LIMIT 1
),
    Updated=TO_TIMESTAMP('2020-08-19 08:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE datepromised IS NULL
;
