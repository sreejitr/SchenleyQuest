package com.example.schenleyquest;

import android.provider.BaseColumns;

public class Contract {
public Contract() {}
	
	public static abstract class Features implements BaseColumns {
		public static final String TABLE_NAME = "features";
		public static final String COLUMN_NAME_FEATURE = "feature";
		public static final String COLUMN_NAME_FEATURE_DESC = "feature_description";
	}
	
	public static abstract class Questions implements BaseColumns {
		public static final String TABLE_NAME = "questions";
		public static final String COLUMN_NAME_QUESTION = "question";
		public static final String COLUMN_NAME_FEATURES_ID = "feature_id";
	}
	
	public static abstract class Options implements BaseColumns {
		public static final String TABLE_NAME = "options";
		public static final String COLUMN_NAME_QUESTION_ID = "question_id";
		public static final String COLUMN_NAME_FEATURES_ID = "feature_id";
		public static final String COLUMN_NAME_OPTION = "option";
	}

}
