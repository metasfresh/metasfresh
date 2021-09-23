import { createServer, Response } from 'miragejs';

export default function () {
  createServer({
    routes() {
      this.post("/app/api/v2/aut", (schema, request) => {
        let attrs = JSON.parse(request.requestBody)
        console.log(attrs)

        return new Response(200, { "Access-Control-Allow-Origin": '*', }, { token: '4bed7f8ec882465e80eb9d23f0f063cf' });
      })
    },
  });
}
