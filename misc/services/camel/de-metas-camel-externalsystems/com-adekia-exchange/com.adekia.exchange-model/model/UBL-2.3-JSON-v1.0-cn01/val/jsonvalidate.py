# JSON schema validation invocation
#
# Uses package:  https://github.com/Julian/jsonschema
#
"""
THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN
NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

Note: for your reference, the above is the "Modified BSD license", this text
      was obtained 2003-07-26 at http://www.xfree86.org/3.3.6/COPYRIGHT2.html#5

THE AUTHOR MAKES NO REPRESENTATION ABOUT THE SUITABILITY OF THIS CODE FOR ANY
PURPOSE.
"""
# $Id: jsonvalidate.py,v 1.12 2016/12/31 13:35:24 admin Exp $

usage = """
Usage:  [-opt]* json-schema json-instance

Options:
 -? - print this message
 --help - print this message
 --q(uiet) - skip printing validation successful message (errors always shown)

"""

import sys, traceback

quietFlag = False

#=========================================================================
#
# Argument processing

badopt = False
while len( sys.argv ) > 1 and sys.argv[1][0] == '-':
    opt = sys.argv[1][1:]
    if opt == '?' or opt == '-help':
        sys.stderr.write( usage )
        sys.exit( 0 )
    elif opt == 'q' or opt == 'quiet':  quietFlag = True
    else:
        print( 'Option "%s" not recognized' % opt )
        badopt = True
    sys.argv[ 1:2 ] = [] # remove argument

if len( sys.argv ) != 3 or badopt: # cannot proceed as requested by user
    sys.stderr.write( usage )
    sys.exit(1)

schemaName = sys.argv[1]
instanceName = sys.argv[2]
    
try: # opening the schema file
    schemaHandle = open( schemaName, "r" )
except IOError, (errno, strerror):
    sys.stderr.write( "I/O schema error(%s): %s\n" % (errno, strerror) )
    sys.exit(1)
except:
    sys.stderr.write( "Unexpected error opening schema: " )
    print >>sys.stderr, re.split("\n",sys.exc_info()[1].__str__())[0]
    sys.exit(1)

try: # opening the instance file
    instanceHandle = open( instanceName, "r" )
except IOError, (errno, strerror):
    sys.stderr.write( "I/O instance error(%s): %s\n" % (errno, strerror) )
    sys.exit(1)
except (errno, strerror):
    sys.stderr.write( "Unexpected error opening instance: " )
    print >>sys.stderr, re.split("\n",sys.exc_info()[1].__str__())[0]
    sys.exit(1)

#=========================================================================
# create JSON objects and validate

import json
import jsonschema
import re
import os

try:
    schema = json.loads(schemaHandle.read())
except:
    sys.stderr.write( "Unexpected error injesting schema: " )
    print >>sys.stderr, re.split("\n",sys.exc_info()[1].__str__())[0]
    sys.exit(1)

try:
    instance = json.loads(instanceHandle.read())
except:
    sys.stderr.write( "Unexpected error injesting instance: " )
    print >>sys.stderr, re.split("\n",sys.exc_info()[1].__str__())[0]
    sys.exit(1)

# the following patched resolver gets around a problem in jsonschema that is
# documented in: https://github.com/Julian/jsonschema/issues/313
schemaAbs = 'file://' + os.path.abspath(schemaName)

class fixResolver( jsonschema.RefResolver):
    def __init__( self ):
      jsonschema.RefResolver.__init__( self,
                                       base_uri = schemaAbs, 
                                       referrer = None )
      self.store[ schemaAbs ] = schema

newResolver = fixResolver()

try:
    jsonschema.validate( instance, schema, resolver=newResolver )
except jsonschema.exceptions.SchemaError as e:
    sys.stderr.write( "Schema error: %s\n" % e.message )
    sys.stderr.write( "validator: %s\n" % e.validator )
    sys.stderr.write( "path: %s\n" % e.path )
    sys.stderr.write( "cause: %s\n" % e.cause )
    sys.stderr.write( "context: %s\n" % e.context )
    sys.stderr.write( "validator_value: %s\n" % e.validator_value )
    sys.stderr.write( "schema_path: %s\n" % e.schema_path )
    sys.stderr.write( "parent: %s\n" % e.parent )
    sys.exit(1)
except jsonschema.exceptions.RefResolutionError as e:
    sys.stderr.write( "Resolution error: %s\n" % e.message )
    sys.exit(1)
except jsonschema.exceptions.ValidationError as e:
    sys.stderr.write( "Validation error: %s\n" % e.message )
    sys.stderr.write( "validator: %s\n" % e.validator )
    sys.stderr.write( "path: %s\n" % e.path )
    sys.stderr.write( "cause: %s\n" % e.cause )
    sys.stderr.write( "context: %s\n" % e.context )
    sys.stderr.write( "validator_value: %s\n" % e.validator_value )
    sys.stderr.write( "schema_path: %s\n" % e.schema_path )
    sys.stderr.write( "parent: %s\n" % e.parent )
    sys.exit(1)

if not quietFlag:
    print >>sys.stderr, "Validation successful"
sys.exit(0)

# end