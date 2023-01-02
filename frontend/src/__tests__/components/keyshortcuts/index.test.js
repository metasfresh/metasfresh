/*
 * #%L
 * metasfresh
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import _Shortcut from '../../../components/keyshortcuts/Shortcut';
import _ShortcutProvider
  from '../../../components/keyshortcuts/ShortcutProvider';
import _generateHotkeys from '../../../components/keyshortcuts/generateHotkeys';
import {
  generateHotkeys,
  Shortcut,
  ShortcutProvider,
} from '../../../components/keyshortcuts/index.js';

describe('keyshortcuts/index.js', () => {
  it('should export ShortcutProvider', () => {
    expect(ShortcutProvider).toEqual(_ShortcutProvider);
  });

  it('should export Shortcut', () => {
    expect(Shortcut).toEqual(_Shortcut);
  });

  it('should export generateHotkeys', () => {
    expect(generateHotkeys).toEqual(_generateHotkeys);
  });
});
