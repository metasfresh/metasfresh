@from:cucumber
@allure.label.epic:E0192_System_Mail_Handling
@allure.label.feature:F00192
@ghActions:run_on_executor4
Feature: Test sending an outbound mail
## F00192: Mail

  Background: Initial Data
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2021-04-16T13:30:13+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
@allure.label.epic:E0192_System_Mail_Handling
@allure.label.feature:F00192
  Scenario: SMTP
    Given email successfully sent
      | mailbox.type | mailbox.smtp.host$env | mailbox.smtp.port$env | mailbox.smtp.auth | mailbox.from$env | mailbox.smtp.username$env | mailbox.smtp.password$env | mailbox.smtp.startTLS$env | to$env         | subject      | message      |
      | smtp         | TEST_SMTP_HOST        | TEST_SMTP_PORT        | true              | TEST_SMTP_FROM   | TEST_SMTP_USER            | TEST_SMTP_PASSWORD        | TEST_SMTP_STARTTLS        | TEST_SMTP_FROM | test subject | test message |
