#!/usr/bin/env bash
# Runs every *-input.txt script and compares the output with its *-expected.txt.
mvn -q compile || exit 1
status=0
for input in src/test/resources/scripts/*-input.txt; do
  name=$(basename "$input" -input.txt)
  expected="src/test/resources/scripts/$name-expected.txt"
  if java -cp target/classes com.lms.Main < "$input" | diff - "$expected" > /dev/null; then
    echo "PASS  $name"
  else
    echo "FAIL  $name"
    java -cp target/classes com.lms.Main < "$input" | diff - "$expected"
    status=1
  fi
done
exit $status
