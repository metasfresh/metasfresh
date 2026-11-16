import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { useQuery } from '../../hooks/useQuery';

const Probe = ({ queryFn, onError }) => {
  const { isPending, error } = useQuery({ queryFn, onError });
  return <div data-testid="state">{isPending ? 'pending' : error ? `error:${error.message}` : 'idle'}</div>;
};

describe('useQuery onError (opt-in)', () => {
  it('reports a failing query to onError, ends pending and exposes the error', async () => {
    const onError = jest.fn();
    render(<Probe queryFn={() => Promise.reject(new Error('boom'))} onError={onError} />);

    await waitFor(() => expect(screen.getByTestId('state').textContent).toBe('error:boom'));
    expect(onError).toHaveBeenCalledTimes(1);
    expect(onError.mock.calls[0][0].message).toBe('boom');
  });
});
