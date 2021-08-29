package police.bharti.katta.util;

import org.json.JSONObject;

import java.util.List;

import police.bharti.katta.model.BhartiModel;
import police.bharti.katta.model.BookModel;
import police.bharti.katta.model.ChaluGhadamodiModel;
import police.bharti.katta.model.EBookModel;
import police.bharti.katta.model.ImportantNoteItemModel;
import police.bharti.katta.model.ImportantNotesMenuModel;
import police.bharti.katta.model.LiveTestModel;
import police.bharti.katta.model.LiveTestQuestionModel;
import police.bharti.katta.model.MagitPrashnPatrikaModel;
import police.bharti.katta.model.SaravMenuModel;
import police.bharti.katta.model.SaravQuestionModel;
import police.bharti.katta.model.TestPaperModel;
import police.bharti.katta.model.TestSeriesModel;
import police.bharti.katta.model.TestSeriesQuestionModel;
import police.bharti.katta.model.TestSeriesResultModel;
import police.bharti.katta.model.VideoBatchModel;
import police.bharti.katta.model.VideoItemModel;
import police.bharti.katta.model.YashoGathaModel;
import police.bharti.katta.view.testserise.TestSeriesResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST(Constants.BHARTI_URL)
    Call<List<BhartiModel>> getBhartiDetails(@Body JSONObject jsonObject);

    @POST(Constants.BHARTI_URL_INDIVISUAL)
    Call<List<BhartiModel>> getBhartiMenuDetails(@Query("mobile") String mobile,@Query("id")String id,@Query("type")String type);

    @POST(Constants.SARAV_MENU_URL)
    Call<List<SaravMenuModel>> getSaravMenu(@Query("mobile") String mobile,@Query("id")String id,@Query("type")String type);

    @POST(Constants.TEST_SERIES_MASTER_MENU)
    Call<List<TestSeriesModel>> getTestMenu(@Query("mobile") String mobile);

    @POST(Constants.BATCH_MASTER)
    Call<List<VideoBatchModel>> getBatchMaster(@Query("mobile") String mobile);


    @POST(Constants.GET_BATCH_VIDEO)
    Call<List<VideoItemModel>> getVideoList(@Query("mobile") String mobile, @Query("id") String id);


    @POST(Constants.TEST_PAPER)
    Call<List<TestPaperModel>> getTestPaper(@Query("mobile") String mobile, @Query("id")String id, @Query("type")String type);

    @POST(Constants.TEST_PAPER_RESULT)
    Call<List<TestSeriesResultModel>> getTestPaperResult(@Query("mobile") String mobile, @Query("id")String id, @Query("type")String type);


    @POST(Constants.LIVE_TEST_PAPER)
    Call<List<LiveTestModel>> getLiveTestPaper(@Query("mobile") String mobile);

    @POST(Constants.INSERTLIVE_TEST_RESULT)
    Call<String> submitLiveTestResult(@Query("testid") String testid,@Query("mobile") String mobile,@Query("correct") String correct,@Query("total") String total,@Query("unanswer") String unanswer,@Query("wrong") String wrong);


    @POST(Constants.INSERT_SARAV_REPORT)
    Call<String> submitSravaReport(@Query("qid") String qid,@Query("mobile") String mobile,@Query("message") String message);


    @POST(Constants.INSERT_TEST_SERIES_RESULT)
    Call<String> submitTestSeriesResult(@Query("testid") String testid,@Query("mobile") String mobile,@Query("correct") String correct,@Query("total") String total,@Query("unanswer") String unanswer,@Query("wrong") String wrong);



    @POST(Constants.SARAV_QUESTIONS_URL)
    Call<List<SaravQuestionModel>> getSaravQuestions(@Query("mobile") String mobile, @Query("id")String id, @Query("type")String type);


    @POST(Constants.GETTESTQUESTION)
    Call<List<TestSeriesQuestionModel>> getTestPaperQuestion(@Query("mobile") String mobile, @Query("id")String id, @Query("type")String type);

    @POST(Constants.GETTESTQUESTION)
    Call<List<LiveTestQuestionModel>> getLivePaperQuestion(@Query("mobile") String mobile, @Query("id")String id, @Query("type")String type);

    @POST(Constants.GET_EBOOK_URL)
    Call<List<EBookModel>> getEBook(@Query("mobile") String mobile, @Query("id")String id, @Query("type")String type);

    @POST(Constants.GET_YASHOGATHA)
    Call<List<YashoGathaModel>> getYashoGatha(@Query("mobile") String mobile, @Query("id")String id, @Query("type")String type);


    @POST(Constants.CHALUGHADAMODI_MENU_URL)
    Call<List<ChaluGhadamodiModel>> getChalughadaModiMenu(@Body JSONObject jsonObject);

    @POST(Constants.MAGIL_PRASHN_PATRIKA)
    Call<List<MagitPrashnPatrikaModel>> getMagilPrashnPatrika(@Body JSONObject jsonObject);



    @POST(Constants.GET_BOOKS_LIST)
    Call<List<BookModel>> getBookList(@Body JSONObject jsonObject);


    @POST(Constants.IMPORTANT_NOTES_URL)
    Call<List<ImportantNotesMenuModel>> getImportantNoteMenu(@Body JSONObject jsonObject);

    @POST(Constants.IMPORTANT_NOTES_ITEM_URL)
    Call<List<ImportantNoteItemModel>> getImportantNotesItem(@Query("mobile") String mobile, @Query("id")String id, @Query("type")String type);


}
