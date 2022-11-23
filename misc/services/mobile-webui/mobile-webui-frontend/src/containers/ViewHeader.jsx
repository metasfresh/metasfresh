import React from 'react';
import { useSelector } from 'react-redux';
import { getEntryItemsFromState, getUserInstructionsFromHeaders } from '../reducers/headers';
import UserInstructions from '../components/UserInstructions';

export const ViewHeader = () => {
  const entryItems = useSelector((state) => getEntryItemsFromState(state));
  const userInstructions = useSelector((state) => getUserInstructionsFromHeaders(state));

  if (entryItems.length <= 0 && !userInstructions) return null;

  return (
    <>
      {entryItems.length > 0 && (
        <table className="table view-header is-size-6">
          <tbody>
            {entryItems.map((entry, i) => (
              <tr key={i}>
                <th>{entry.caption}</th>
                <td>{entry.value}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
      {userInstructions && <UserInstructions text={userInstructions} />}
    </>
  );
};
