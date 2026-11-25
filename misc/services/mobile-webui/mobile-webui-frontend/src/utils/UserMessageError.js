/**
 * An error whose text the user shall read: `messageKey` is a translation key, `details` (optional) the reason shown after it, `context` (optional) data for the UI trace only.
 * `toastErrorFromObj` shows it as that message. Unlike a plain `{ messageKey }` object it has a stack trace and is an `Error`.
 */
export class UserMessageError extends Error {
  constructor(messageKey, details, context) {
    super(details ? `${messageKey}: ${details}` : messageKey);
    this.name = 'UserMessageError';
    this.messageKey = messageKey;
    this.details = details;
    this.context = context;
  }
}
