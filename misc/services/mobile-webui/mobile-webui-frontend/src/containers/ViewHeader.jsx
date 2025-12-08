import React from 'react';
import { useHeaders } from '../reducers/headers';
import UserInstructions from '../components/UserInstructions';

export const ViewHeader = () => {
  const { userInstructions, entryItems } = useHeaders();

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
