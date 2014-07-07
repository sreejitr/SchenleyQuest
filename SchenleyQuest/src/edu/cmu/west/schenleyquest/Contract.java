package edu.cmu.west.schenleyquest;

import android.provider.BaseColumns;

public class Contract {
public Contract() {}
	
	public static abstract class Routes implements BaseColumns {
		public static final String TABLE_NAME = "routes";
		public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
		public static final String COLUMN_NAME_DIFFICULTY_LEVEL = "difficulty_level";
		public static final String COLUMN_NAME_QUESTIONID_SET = "questionid_set";
	}

	public static abstract class Features implements BaseColumns {
		public static final String TABLE_NAME = "features";
		public static final String COLUMN_NAME_FEATURE = "feature";
		public static final String COLUMN_NAME_FEATURE_DESC = "feature_description";
		public static final String COLUMN_NAME_FEATURE_HINT = "hint";
		public static final String COLUMN_NAME_LATITUDE = "latitude";
		public static final String COLUMN_NAME_LONGITUDE = "longitude";
	}
	
	public static abstract class Questions implements BaseColumns {
		public static final String TABLE_NAME = "questions";
		public static final String COLUMN_NAME_QUESTION = "question";
		public static final String COLUMN_NAME_FEATURE_ID = "feature_id";
	}
	
	public static abstract class Options implements BaseColumns {
		public static final String TABLE_NAME = "options";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
		public static final String COLUMN_NAME_FEATURE_ID = "feature_id";
		public static final String COLUMN_NAME_OPTION = "option";
	}
	
	public static abstract class HighScores implements BaseColumns {
		public static final String TABLE_NAME = "highscores";
		public static final String COLUMN_NAME_ROUTE_ID = "route_id";
		public static final String COLUMN_NAME_DIFFICULTY_LEVEL = "difficulty_level";
		public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
		public static final String COLUMN_NAME_SCORE = "score";
	}
	
	public static abstract class Badges implements BaseColumns {
		public static final String TABLE_NAME = "badges";
		public static final String COLUMN_NAME_ROUTE_ID = "route_id";
		public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
		public static final String COLUMN_NAME_DIFFICULTY_LEVEL = "difficulty_level";
		public static final String COLUMN_NAME_BADGE = "badge";
		public static final String COLUMN_NAME_NO_OF_BADGES = "no_of_badges";
	}
}
