<?php

namespace Database\Seeders;

use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Carbon;
use Illuminate\Support\Facades\Hash;

class UseerSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $date = Carbon::now()->format('Y-m-d H:i:s');
        $user = [
            array('role_id' => '1', 'name' => 'admin', 'email' => 'admin@gmail.com', 'password' => Hash::make('225588Ab'),  'created_at' => $date, 'updated_at' => $date),
        ];
        User::insert($user);
    }
}
