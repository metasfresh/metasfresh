import { mergePartialGroupToGroupsArray } from '../../../utils/documentReferencesHelper';

describe('mergePartialGroupToGroupsArray', () => {
  it('add to empty array', () => {
    const existingGroups = [];
    const partialGroupToAdd = {
      caption: 'group 1',
      miscGroup: false,
      references: [ { targetWindowId: '1', caption: 'item 1', priority: 555 } ],
    };
    const result = mergePartialGroupToGroupsArray(existingGroups, partialGroupToAdd);

    expect(result).toEqual(expect.arrayContaining([partialGroupToAdd]));
  });

  it('add new group', () => {
    const existingGroups = [
      {
        caption: 'group B',
        miscGroup: false,
        references: [ { targetWindowId: '1', caption: 'item 1', priority: 555 } ],
      },
    ];
    const partialGroupToAdd = {
      caption: 'group A',
      miscGroup: false,
      references: [ { targetWindowId: '1', caption: 'item 1', priority: 555 } ],
    };
    const result = mergePartialGroupToGroupsArray(existingGroups, partialGroupToAdd);

    expect(result).toEqual(expect.arrayContaining([
      {
        caption: 'group A',
        miscGroup: false,
        references: [ { targetWindowId: '1', caption: 'item 1', priority: 555 } ],
      },
      {
        caption: 'group B',
        miscGroup: false,
        references: [ { targetWindowId: '1', caption: 'item 1', priority: 555 } ],
      },
    ]));
  });

  it('add miscGroup', () => {
    const existingGroups = [
      {
        caption: 'group Z',
        miscGroup: false,
        references: [ { targetWindowId: '1', caption: 'item 1', priority: 555 } ],
      },
    ];
    const partialGroupToAdd = {
      caption: 'group A',
      miscGroup: true,
      references: [ { targetWindowId: '1', caption: 'item 1', priority: 555 } ],
    };
    const result = mergePartialGroupToGroupsArray(existingGroups, partialGroupToAdd);

    expect(result).toEqual(expect.arrayContaining([
      {
        caption: 'group Z',
        miscGroup: false,
        references: [ { targetWindowId: '1', caption: 'item 1', priority: 555 } ],
      },
      {
        caption: 'group A',
        miscGroup: true,
        references: [ { targetWindowId: '1', caption: 'item 1', priority: 555 } ],
      },
    ]));
  });

  it('add to existing group', () => {
    const existingGroups = [
      {
        caption: 'group A',
        miscGroup: false,
        references: [ { targetWindowId: '1', caption: 'item 1', priority: 555 } ],
      },
    ];
    const partialGroupToAdd = {
      caption: 'group A',
      miscGroup: false,
      references: [ { targetWindowId: '2', caption: 'item 2', priority: 555 } ],
    };
    const result = mergePartialGroupToGroupsArray(existingGroups, partialGroupToAdd);

    expect(result).toEqual(expect.arrayContaining([
      {
        caption: 'group A',
        miscGroup: false,
        references: [ 
          { targetWindowId: '1', caption: 'item 1', priority: 555 },
          { targetWindowId: '2', caption: 'item 2', priority: 555 },
        ],
      },
    ]));
  });
});
