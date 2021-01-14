import { store } from '../models/Store';

export function translate(toTanslate: string): string {
  const messages = store.i18n.getMessages;
  return messages[toTanslate];
}
