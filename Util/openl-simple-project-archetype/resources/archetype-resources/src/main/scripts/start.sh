#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
${symbol_pound}!/bin/sh

java -Dopenl-cmd=run -classpath libs/*;openl/lib/* ${package}.Main
