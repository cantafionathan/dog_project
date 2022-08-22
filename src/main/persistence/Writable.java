package persistence;

import org.json.JSONObject;

// Based on JsonSerializationDemo's Writable interface
// Repository: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
