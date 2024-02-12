import {
  getTab,
  updateTab
} from '../../utils';

describe('General helpers', () => {
  describe('getTab', () => {
    it('get tab from a flat array', () => {
      const tab3 = { tabId: '3c' };
      const tabsData = {
        tabs: [
          { tabId: '1a' },
          { tabId: '2b' },
          tab3,
        ],
      };
      expect(getTab(tabsData, '3c')).toEqual(tab3);
    });

    it('find tab in a nested array', () => {
      const tab32 = { tabId: '3c2' };
      const tabsData = {
        tabs: [
          { tabId: '1a' },
          { tabId: '2b' },
          {
            tabId: '3c',
            tabs: [
              { tabId: '3c1' },
              tab32,
              { tabId: '3c3' },
            ],
          }
        ],
      };

      expect(getTab(tabsData, '3c2')).toEqual(tab32); 
    });

    it(`return null, when tab can't be found`, () => {
      const tabsData = {
        tabs: [
          { tabId: '1a' },
          { tabId: '2b' },
          { tabId: '3c' },
        ],
      };

      expect(getTab(tabsData, '4d')).toBeFalsy();
    });

    it(`return null, when nested tab can't be found`, () => {
      const tabsData = {
        tabs: [
          { tabId: '1a' },
          { tabId: '2b' },
          {
            tabId: '3c',
            tabs: [
              { tabId: '3c1' },
              { tabId: '3c2' },
              { tabId: '3c3' },
            ],
          }
        ],
      };

      expect(getTab(tabsData, '3c4')).toBeFalsy();
    });
  });

  describe.only('updateTab', () => {
    it('updates tab in a flat array', () => {
      const changes = { val: 'bar', val1: 'baz' };
      const tabsData = [
        { tabId: '1a', val: 'foo' },
        { tabId: '2b', val: 'foo' },
        { tabId: '3c', val: 'foo' },
      ];

      expect(updateTab(tabsData, '2b', changes)).toEqual(expect.arrayContaining([expect.objectContaining(changes)]));
    });

    it('updates tab in a nested array', () => {
      const changes = { val: 'bar', val1: 'baz' };
      const tabsData = {
        tabs: [
          { tabId: '1a', val: 'foo' },
          { tabId: '2b', val: 'foo' },
          {
            tabId: '3c',
            val: 'foo',
            tabs: [
              { tabId: '3c1', val: 'foo' },
              { tabId: '3c2', val: 'foo' },
              { tabId: '3c3', val: 'foo' },
            ],
          }
        ],
      };

      expect(updateTab(tabsData.tabs, '3c1', changes)).toEqual(
        expect.arrayContaining([
          expect.objectContaining(
            { tabs: expect.arrayContaining([expect.objectContaining(changes)]) },
          )
        ])
      );
    });
  });
});