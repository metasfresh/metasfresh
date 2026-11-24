/**
 * An error whose text the user shall read: `messageKey` is a translation key, `details` (optional) the reason shown after it.
 * `toastErrorFromObj` shows it as that message. Unlike a plain `{ messageKey }` object it has a stack trace and is an `Error`.
 */
export class UserMessageError extends Error {
  constructor(messageKey, details) {
    super(details ? `${messageKey}: ${details}` : messageKey);
    this.name = 'UserMessageError';
    this.messageKey = messageKey;
    this.details = details;
  }
}
