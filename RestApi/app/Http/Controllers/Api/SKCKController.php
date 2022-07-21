<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Skck;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;
use Exception;

class SKCKController extends Controller
{
    public function tambah(Request $request)
    {
        $rules = array(
            'nik' => 'required|max:255',
            'nama' => 'required|max:255',
            'kecamatan' => 'required|max:255',
            'status' => 'required|max:255',
            'jk' => 'required|max:255',
        );
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            DB::beginTransaction();
            try{
                $skck = new Skck();
                $skck->nik = $request->nik;
                $skck->nama = $request->nama;
                $skck->kecamatan = $request->kecamatan;
                $skck->status = $request->status;
                $skck->jk = $request->jk;
                $skck->save();
                DB::commit();
                return response()->json([
                    'status' => true,
                    'message' => 'Berhasil menyimpan data',
                ],200);
            } catch (Exception $e) {
                DB::rollback();
                return response()->json([
                    'status' => false,
                    'message' => $e->getMessage(),
                ]);
            }
        }
    }

    public function get_skck(Request $request)
    {
        $rules = array(
            'cari' => 'max:255',
        );
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            if(isset($request->cari)){
                $skck = Skck::where('nik','like',$request->cari)->orWhere('nama','like','%'.$request->cari.'%')->limit(20)->get();
            } else {
                $skck = Skck::limit(20)->get();
            }


            return response()->json([
                'status' => true,
                'message' => 'Berhasil menyimpan data',
                'result' => $skck,
                'jumlah' => $skck->count(),
            ],200);
        }
    }
}
