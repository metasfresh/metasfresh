/* eslint-env mocha */
import { expect } from "chai";

import _Shortcut from "./Shortcut";
import _ShortcutProvider from "./ShortcutProvider";
import _generateHotkeys from "./generateHotkeys";
import { generateHotkeys, Shortcut, ShortcutProvider } from "./index.js";

describe("Shortcuts/index.js", () => {
  it("should export ShortcutProvider", () => {
    expect(ShortcutProvider).to.equal(_ShortcutProvider);
  });

  it("should export Shortcut", () => {
    expect(Shortcut).to.equal(_Shortcut);
  });

  it("should export generateHotkeys", () => {
    expect(generateHotkeys).to.equal(_generateHotkeys);
  });
});
