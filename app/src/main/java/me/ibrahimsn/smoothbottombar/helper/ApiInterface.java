package me.ibrahimsn.smoothbottombar.helper;

import com.google.gson.JsonObject;

import java.util.Map;

import me.ibrahimsn.smoothbottombar.model.UserModel;
import me.ibrahimsn.smoothbottombar.model.UserTwoModel;
import me.ibrahimsn.smoothbottombar.model.bbpsServiceModel.BbpsServiceModel;
import me.ibrahimsn.smoothbottombar.model.tab.ModelRequestdetails;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

//    http://cbisolar.vidhyalaya.co.in/Api/Api/customerlogin?mobno=9828775444&password=1234&remember_token=1234
//    http://cbisolar.vidhyalaya.co.in/Api/Api/customerlogin?mobno="9828775444"&password="1234"&remember_token="1234"



    @FormUrlEncoded
    @POST(ApiContants.dmtRegisterAuth)
    Call<JsonObject> dmtRegisterAuth(@Field("user_id") String user_id,
                                     @Field("first_name") String first_name,
                                     @Field("last_name") String last_name,
                                     @Field("mobile") String mobile,
                                     @Field("dob") String dob,
                                     @Field("address") String address,
                                     @Field("pin_code") String pin_code);

    @FormUrlEncoded
    @POST(ApiContants.dmtLoginAuth)
    Call<JsonObject> dmtLoginAuth(@Field("user_id") String user_id,
                                  @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(ApiContants.dmtBeneficiaryAuth)
    Call<JsonObject> dmtBeneficiaryAuth(@Field("user_id") String user_id,
                                        @Field("account_holder_name") String account_holder_name,
                                        @Field("ben_mobile") String ben_mobile,
                                        @Field("account_no") String account_no,
                                        @Field("ifsc") String ifsc,
                                        @Field("bankID") String bankID,
                                        @Field("is_verify") String is_verify,
                                        @Field("remitter_mobile") String remitter_mobile);


    @FormUrlEncoded
    @POST(ApiContants.dmtOtpVerifyAuth)
    Call<JsonObject> dmtOtpVerifyAuth(
            @Field("user_id") String user_id,
            @Field("otp_code") String otp_code,
            @Field("token") String token);

    @FormUrlEncoded
    @POST(ApiContants.dmtBeneficiaryList)
    Call<JsonObject> dmtBeneficiaryList(
            @Field("user_id") String user_id,
            @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(ApiContants.dmtBeneficiaryDeleteAuth)
    Call<JsonObject> dmtBeneficiaryDeleteAuth(
            @Field("user_id") String user_id,
            @Field("benId") String benId,
            @Field("remitter_mobile") String remitter_mobile);


    @FormUrlEncoded
    @POST(ApiContants.dmtTransferAuth)
    Call<JsonObject> dmtTransferAuth(
            @Field("user_id") String user_id,
            @Field("benId") String benId,
            @Field("amount") String amount,
            @Field("is_verify") String is_verify);

    //////////////////////////////////////////////////////////////////////////////////

    @FormUrlEncoded
    @POST(ApiContants.xdmtRegisterAuth)
    Call<JsonObject> xdmtRegisterAuth(@Field("user_id") String user_id,
                                     @Field("first_name") String first_name,
                                     @Field("last_name") String last_name,
                                     @Field("mobile") String mobile,
                                     @Field("dob") String dob,
                                     @Field("address") String address,
                                     @Field("pin_code") String pin_code);

    @FormUrlEncoded
    @POST(ApiContants.xdmtLoginAuth)
    Call<JsonObject> xdmtLoginAuth(@Field("user_id") String user_id,
                                  @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(ApiContants.xdmtBeneficiaryAuth)
    Call<JsonObject> xdmtBeneficiaryAuth(@Field("user_id") String user_id,
                                        @Field("account_holder_name") String account_holder_name,
                                        @Field("ben_mobile") String ben_mobile,
                                        @Field("account_no") String account_no,
                                        @Field("ifsc") String ifsc,
                                        @Field("bankID") String bankID,
                                        @Field("is_verify") String is_verify,
                                        @Field("remitter_mobile") String remitter_mobile);


    @FormUrlEncoded
    @POST(ApiContants.xdmtOtpVerifyAuth)
    Call<JsonObject> xdmtOtpVerifyAuth(
            @Field("user_id") String user_id,
            @Field("otp_code") String otp_code,
            @Field("token") String token);

    @FormUrlEncoded
    @POST(ApiContants.xdmtBeneficiaryList)
    Call<JsonObject> xdmtBeneficiaryList(
            @Field("user_id") String user_id,
            @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(ApiContants.xdmtBeneficiaryDeleteAuth)
    Call<JsonObject> xdmtBeneficiaryDeleteAuth(
            @Field("user_id") String user_id,
            @Field("benId") String benId,
            @Field("remitter_mobile") String remitter_mobile);


    @FormUrlEncoded
    @POST(ApiContants.xdmtTransferAuth)
    Call<JsonObject> xdmtTransferAuth(
            @Field("user_id") String user_id,
            @Field("benId") String benId,
            @Field("amount") String amount,
            @Field("is_verify") String is_verify);


/////////////////////////////////////////////////////////////////////////////////////////

    @GET(ApiContants.getDmtBankList)
    Call<JsonObject> getDmtBankList();

    @FormUrlEncoded
    @POST(ApiContants.getOperatorList)
    Call<JsonObject> getOperator(
            @Field("type") String customer_id);//Prepaid


//    @FormUrlEncoded
    @GET(ApiContants.getCircleList)
    Call<JsonObject> getCircleList();//Prepaid





    @FormUrlEncoded
    @POST(ApiContants.rechargeAuth)
    Call<JsonObject> rechargeAuth(
            @Field("user_id") String user_id,
            @Field("mobile") String mobile,
            @Field("operator") String operator,
            @Field("amount") String amount,
            @Field("circle_code") String circle_code,
            @Field("txn_pass") String txn_pass);

    @GET(ApiContants.getOperatorList)
    Call<JsonObject> signIn(
            @Query("username") String mobno,
            @Query("password") String password);



    @FormUrlEncoded
    @POST(ApiContants.loginAuth)
    Call<UserModel> loginAuth(@Field("username") String username,
                              @Field("password") String password);



    @FormUrlEncoded
    @POST(ApiContants.userDetail)
    Call<UserTwoModel> userDetail(@Field("user_id") String user_id);





    @FormUrlEncoded
    @POST(ApiContants.getBbpsServiceFormParams)
    Call<JsonObject> getBbpsServiceFormParams(
            @Field("biller_id") String biller_id,
            @Field("service_id") String service_id);







//    @FormUrlEncoded
//    @POST(ApiContants.bbpsBillFetchAuth)
//    Call<JsonObject> bbpsBillFetchAuth(
//            @Field("user_id") String user_id,
//            @Field("service_id") String service_id,
//            @Field("biller_id") String biller_id,
//            @Field("para1") String para1);

    @FormUrlEncoded
    @POST(ApiContants.bbpsBillFetchAuth)
    Call<JsonObject> bbpsBillFetchAuth(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST(ApiContants.serviceBillPayAuth)
    Call<JsonObject> serviceBillPayAuth(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST(ApiContants.getOperatorId)
    Call<JsonObject> getOperatorId(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST(ApiContants.getBbpsServiceOperator)
    Call<BbpsServiceModel> getBbpsServiceOperator(@Field("service_id") String service_id);

    @FormUrlEncoded
    @POST(ApiContants.getPlanList)
    Call<ModelRequestdetails> GetPlandetails(@Field("operator") String operator,
                                             @Field("circle") String circle);
//    QPR652794 146129


    /*{
    "status": 1,
    "message": "Logged in Successfully.",
    "is_otp": 0,
    "user_detail": {
        "account_id": "1",
        "user_id": "3",
        "user_type": "5",
        "user_code": "QPR652794",
        "name": "Test",
        "email": "test@gmail.com",
        "mobile": "88888888",
        "wallet_balance": "90",
        "e_wallet_balance": 0,
        "profile_photo": "https://harshpay.net/",
        "fcm_id": "",
        "is_recharge_active": 0,
        "is_transfer_active": 0,
        "is_aeps_active": 0,
        "is_bbps_live_active": 0
    }
}*/
    @POST(ApiContants.PREF_updateNkk)
    Call<JsonObject> updateNkk(@Body String request);

    @POST(ApiContants.PREF_AddNkk)
    Call<JsonObject> addNkk(@Body String request);

    @POST(ApiContants.PREF_attandance)
    Call<JsonObject> attandance(@Body String request);

    @GET(ApiContants.PREF_deletess)
    Call<JsonObject> deletess(@Query("ss_id") Integer ss_id);

    @GET(ApiContants.PREF_dashboard)
    Call<JsonObject> dashboard(@Query("id") Integer id, @Query("user_id") Integer user_id, @Query("flag") String flag
            , @Query("flag_id") Integer flag_id);

//    @GET(ApiContants.PREF_dashboard)
//    Call<DashBoardMainModel> dashboardList(@Query("id") Integer id, @Query("user_id") Integer user_id, @Query("flag") String flag
//            , @Query("flag_id") Integer flag_id);

    @GET(ApiContants.PREF_dashboard)
    Call<JsonObject> dashboardShakaList(@Query("id") Integer id);
//
    @GET(ApiContants.PREF_dashboard)
    Call<JsonObject> dashboardShakaList(@Query("id") Integer id, @Query("user_id") Integer user_id, @Query("flag") String flag
            , @Query("flag_id") Integer flag_id);

    @GET(ApiContants.PREF_getExcel)
    Call<ResponseBody> getExcel(@Query("shakha_id") Integer shakha_id);



//    @FormUrlEncoded
//    @Headers("Content-Type: application/json")
//    @POST(ApiContants.PREF_regis)
//    Call<JsonObject> formRegistration(
//            @Field("name") String name,
//            @Field("lastname") String lastname,
//            @Field("occupation") String occupation,
//            @Field("address") String address,
//            @Field("mobile") String mobile,
//            @Field("whatsapp") String whatsapp,
//            @Field("dob") String dob,
//            @Field("blood_group") String blood_group,
//            @Field("shikshan") String shikshan,
//            @Field("shikshan_year") String shikshan_year,
//            @Field("pre_respon") String pre_respon,
//            @Field("oauth") Integer oauth,
//            @Field("ghosh") Integer ghosh,
//            @Field("vidhik_present") String vidhik_present,
//            @Field("vidhik_past") String vidhik_past,
//            @Field("createdy") Integer createdy,
//            @Field("updateddy") Integer updateddy,
//            @Field("shakha") Integer shakha,
//            @Field("other") String other,
//            @Field("flag") String flag
//            );

    @FormUrlEncoded
    @POST(ApiContants.PREF_attandance)
    Call<JsonObject> attandance(
            @Field("date") String date,
            @Field("shakha") String shakha,
            @Field("pravas") String pravas,
            @Field("pravashi_name") String pravashi_name,
            @Field("sankhya") String sankhya,
            @Field("flag") String flag
            );

    @FormUrlEncoded
    @POST(ApiContants.PREF_profileupdate)
    Call<JsonObject> profileupdate(
            @Field("customer_id") String customer_id,
            @Field("address") String address);

    @FormUrlEncoded
    @POST(ApiContants.PREF_passwordchange)
    Call<JsonObject> passwordchange(
            @Field("customer_id") String customer_id,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST(ApiContants.changePassword)
    Call<JsonObject> changePassword(
            @Field("userID") String userID,
            @Field("opw") String opw,
            @Field("npw") String npw,
            @Field("cpw") String cpw);

    @GET(ApiContants.PREF_StoreList)
    Call<JsonObject> listStore();

    @GET(ApiContants.PREF_winner_list)
    Call<JsonObject> winner_list();

    @GET(ApiContants.PREF_notification)
    Call<JsonObject> notification();

    @GET(ApiContants.PREF_product_list)
    Call<JsonObject> product_list();

    @GET(ApiContants.PREF_banner_list)
    Call<JsonObject> banner_list();

    @FormUrlEncoded
    @POST(ApiContants.PREF_UserAPI)
    Call<JsonObject> agent_otp_login(@Field("action") String action,
                                     @Field("cell") String cell);

    @FormUrlEncoded
    @POST(ApiContants.PREF_ClinicAPI)
    Call<JsonObject> list_doc_clinic(@Field("action") String action,
                                     @Field("clinicid") String cell,
                                     @Field("ref_code") String ref_code);

    @FormUrlEncoded
    @POST(ApiContants.PREF_UserAPI)
    Call<JsonObject> otp_login(@Field("action") String action,
                               @Field("cell") String cell);

    @FormUrlEncoded
    @POST(ApiContants.PREF_ClinicAPI)
    Call<JsonObject> list_clinic(@Field("action") String action,
                               @Field("ref_code") String ref_code,
                               @Field("ref_id") String ref_id
    );

    @FormUrlEncoded
    @POST(ApiContants.PREF_ClinicAPI)
    Call<JsonObject> get_clinic(@Field("action") String action,
                               @Field("clinicid") String ref_code
    );

    @FormUrlEncoded
    @POST(ApiContants.PREF_new_prospect)
    Call<JsonObject> new_prospect(
            @Field("action") String action,
            @Field("ref_code") String ref_code,
            @Field("providername") String providername,
            @Field("email") String email,
            @Field("dob") String dob,
            @Field("gender") String gender,
            @Field("cell") String cell);

    @FormUrlEncoded
    @POST(ApiContants.PREF_ClinicAPI)
    Call<JsonObject>    new_clinic(
            @Field("action") String action,
            @Field("ref_code") String ref_code,
            @Field("ref_id") String ref_id,
            @Field("name") String name,
            @Field("address1") String address1,
            @Field("address2") String address2,
            @Field("address3") String address3,
            @Field("city") String city,
            @Field("st") String st,
            @Field("pin") String pin,
            @Field("cell") String cell,
            @Field("telephone") String telephone,
            @Field("email") String email,
            @Field("status") String status,
            @Field("website") String website,
            @Field("gps_location") String gps_location,
            @Field("whatsapp") String whatsapp,
            @Field("facebook") String facebook,
            @Field("twitter") String twitter,
            @Field("primary") String primary,
            @Field("dentalchairs") String dentalchairs,
            @Field("auto_clave") String auto_clave,
            @Field("implantology") String implantology,
            @Field("instrument_sterilization") String instrument_sterilization,
            @Field("waste_displosal") String waste_displosal,
            @Field("suction_machine") String suction_machine,
            @Field("laser") String laser,
            @Field("RVG_OPG") String RVG_OPG,
            @Field("radiation_protection") String radiation_protection,
            @Field("computers") String computers,
            @Field("network") String network,
            @Field("internet") String internet,
            @Field("air_conditioned") String air_conditioned,
            @Field("waiting_area") String waiting_area,
            @Field("backup_power") String backup_power,
            @Field("toilet") String toilet,
            @Field("water_filter") String water_filter,
            @Field("parking_facility") String parking_facility,
            @Field("receptionist") String receptionist,
            @Field("credit_card") String credit_card,
            @Field("certifcates") String certifcates,
            @Field("emergency_drugs") String emergency_drugs,
            @Field("infection_control") String infection_control,
            @Field("daily_autoclaved") String daily_autoclaved,
            @Field("patient_records") String patient_records,
            @Field("patient_consent") String patient_consent,
            @Field("patient_traffic") String patient_traffic,
            @Field("nabh_iso_certifcation") String nabh_iso_certifcation,
            @Field("mdp_registration") String mdp_registration,
            @Field("intra_oral_camera") String intra_oral_camera,
            @Field("rotary_endodontics") String rotary_endodontics
    );

    @FormUrlEncoded
    @POST(ApiContants.PREF_appointmentAPI)
    Call<JsonObject> new_appointment(
            @Field("action") String action,
            @Field("providerid") String providerid,
            @Field("doctorid") String doctorid,
            @Field("clinicid") String clinicid,
            @Field("memberid") String memberid,
            @Field("patientid") String patientid,
            @Field("cell") String cell,
            @Field("complaint") String complaint,
            @Field("appointment_start") String appointment_start,
            @Field("duration") String duration,
            @Field("notes") String notes
    );

    @FormUrlEncoded
    @POST(ApiContants.PREF_ClinicAPI)
    Call<JsonObject> delete_clinic(
            @Field("action") String action,
            @Field("clinicid") String clinicid
    );

/*
            @Part("auto_clave") RequestBody auto_clave,
            @Part("implantology") RequestBody implantology,
            @Part("instrument_sterilization") RequestBody instrument_sterilization,
            @Part("waste_displosal") RequestBody waste_displosal,
            @Part("suction_machine") RequestBody suction_machine,
            @Part("laser") RequestBody laser,
            @Part("RVG_OPG") RequestBody RVG_OPG,
            @Part("radiation_protection") RequestBody radiation_protection,
            @Part("computers") RequestBody computers,
            @Part("network") RequestBody network,
            @Part("internet") RequestBody internet,
            @Part("air_conditioned") RequestBody air_conditioned,
            @Part("waiting_area") RequestBody waiting_area,
            @Part("backup_power") RequestBody backup_power,
            @Part("toilet") RequestBody toilet,
            @Part("water_filter") RequestBody water_filter,
            @Part("parking_facility") RequestBody parking_facility,
            @Part("receptionist") RequestBody receptionist,
            @Part("credit_card") RequestBody credit_card,
            @Part("certifcates") RequestBody certifcates,
            @Part("emergency_drugs") RequestBody emergency_drugs,
            @Part("infection_control") RequestBody infection_control,
            @Part("daily_autoclaved") RequestBody daily_autoclaved,
            @Part("patient_records") RequestBody patient_records,
            @Part("patient_consent") RequestBody patient_consent,
            @Part("patient_traffic") RequestBody patient_traffic,
            @Part("nabh_iso_certifcation") RequestBody nabh_iso_certifcation,
            @Part("mdp_registration") RequestBody mdp_registration,
            @Part("intra_oral_camera") RequestBody intra_oral_camera,
            @Part("rotary_endodontics") RequestBody rotary_endodontics
   /*         @Part MultipartBody.Part file);
*/

}