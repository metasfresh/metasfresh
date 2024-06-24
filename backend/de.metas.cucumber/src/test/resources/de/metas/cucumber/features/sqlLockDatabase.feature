@from:cucumber
@ghActions:run_on_executor7
Feature: DB-Based Locking
  As a developer
  I want to use a locking mechanism for data records

  @from:cucumber
  Scenario: Acquiring a lock on an not-yet-locked record succeeds
    Given I_AD_PInstance with id 123124 is created
    When a lock on the record with AD_Table 'AD_PInstance' and Record_ID 123124 is requested for a lock owner with prefix 'cucumber1a'
    Then the lock request was successful and the lock owner has the prefix 'cucumber1a'

  @from:cucumber
  Scenario: Acquiring a lock on an already-locked record fails
    Given I_AD_PInstance with id 123125 is created
    And a lock on the record with AD_Table 'AD_PInstance' and Record_ID 123125 is requested for a lock owner with prefix 'cucumber1b'
    And the lock request was successful and the lock owner has the prefix 'cucumber1b'
    When a lock on the record with AD_Table 'AD_PInstance' and Record_ID 123125 is requested for a lock owner with prefix 'cucumber2b'
    Then the lock request failed
    And a 'LockFailedException' LockException was thrown that contains the previously acquired lock on the record with AD_Table 'AD_PInstance' and Record_ID 123125 and a lock owner with prefix 'cucumber1b'
